#!/usr/bin/perl

use strict;
use warnings;
use XML::Simple;

# Read the input XML file
my $xml_file = shift @ARGV;
my $xml = XMLin($xml_file);

my $num_problems = 0;
my $num_correct = 0;
my $num_incorrect = 0;

# Open the output text file for writing
open(my $fh, '>', 'validationResults.txt') or die "Could not open validationResults.txt: $!";

# Loop through each problem in the XML file and solve it
foreach my $problem (@{$xml->{problem}}) {
    my $num1 = $problem->{num1};
    my $num2 = $problem->{num2};
    my $operator = $problem->{operation};
    my $expected_result = $problem->{result};

    # Calculate the answer to the problem
    my $actual_result;
    if ($operator eq 'add') {
        $actual_result = $num1 + $num2;
#    } elsif ($operator eq 'subtract') {
#        $actual_result = $num1 - $num2;
#    } elsif ($operator eq 'multiply') {
#        $actual_result = $num1 * $num2;
#    } elsif ($operator eq 'divide') {
#        $actual_result = $num1 / $num2;
    }

    # Check if the answer is correct
    if ($expected_result == $actual_result) {
        $num_correct++;
        print "Correct!: The answer to $num1 $operator $num2 is listed as $expected_result, and the truth value was calculated as $actual_result\n";
        print {$fh} "Correct!: The answer to $num1 $operator $num2 is listed as $expected_result, and the truth value was calculated as $actual_result\n";
    } else {
        $num_incorrect++;
        print  "Error: The answer to $num1 $operator $num2 is listed as $expected_result, but the truth value was calculated as$actual_result\n";
        print {$fh} "Error: The answer to $num1 $operator $num2 is listed as $expected_result, but the truth value was calculated as$actual_result\n";
    }

    $num_problems++;
}


# Report the overall results
print "Total problems: $num_problems\n";
print "Number correct: $num_correct\n";
print "Number incorrect: $num_incorrect\n";
print {$fh} "Total problems: $num_problems\n";
print {$fh} "Number correct: $num_correct\n";
print {$fh} "Number incorrect: $num_incorrect\n";


# Close the output text file
close($fh);
