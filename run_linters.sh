#!/bin/bash

# Define the linting folder and output file
LINTING_FOLDER="linting"
OUTPUT_FILE="full_report.out"

# Clear the output file if it exists
> $OUTPUT_FILE

# Compile Java file
bash "./compile.sh"

# Iterate through each subdirectory in the linting folder
for dir in $LINTING_FOLDER/*/; do
    # Get the name of the subdirectory
    dir_name=$(basename "$dir")
    
    # Run the script inside the subdirectory
    script_file="$dir/run_linter.sh"
    if [ -f "$script_file" ]; then
        pushd "$dir" > /dev/null
        bash "./run_linter.sh"
        popd > /dev/null
        
        # Read the output from the text file inside the subdirectory
        output_file="$dir/report.out"
        if [ -f "$output_file" ]; then
            echo "<<<<<$dir_name>>>>>" >> $OUTPUT_FILE
            cat "$output_file" >> $OUTPUT_FILE
            echo "" >> $OUTPUT_FILE
        else
            echo "Output file not found in $dir_name" >> $OUTPUT_FILE
        fi
    else
        echo "Script file not found in $dir_name" >> $OUTPUT_FILE
    fi
done