# Project 3: Regular Expressions

* Author: Benjamin Warner
* Class: CS361 Section 002
* Semester: Fall 2021

## Overview

This program constructs an NFA from a given regular expression using a 
variation of the recursive descent parsing algorithm.

A command line driver is provided through REDriver.java, which will read 
a regular expression and several strings to test for the completeness
of the program. This driver takes a single positional argument: a plain-text
file that contains the regular expression and test strings.

The NFA is constructed by using a variation of the recursive descent algorithm.
A bare bones parser is provided that will parse single characters from the 
regular expression given. When the '(' character is encountered, the algorithm
will recurse into itself to build up a single term for the group of parantheses. 

## Compiling and Running

To build the driver, run the below command:

`javac -cp ".:./CS361FA.jar" re/REDriver.java`

To run the compiled driver, run the below command:

`java -cp ".:./CS361FA.jar" re.REDriver path/to/input/file.txt`

An input file should consist of a regular expression, on a single line,
then followed by the strings to test. A regular expression consists of only
seven symbols: a, b, e, |, (, ), and *.

* e represents the empty string
* | represents the union operator
* `*` represents the star operator (i.e. repetition)

The characters a and b represent the alphabet while ( and ) set precedence for the operations.

### Example Input File

```
(a*|b)b
e
aaa
bb
b
aaaab
bbbbba
```

## Discussion

This program wasn't very difficult to do, mostly because the resources provided to implement
the algorithm were quite helpful and elucidating. The only thing I've really struggled with is
nested parantheses (which I've pretty much just ignored). Otherwise, the program works rather well
for simple regular expressions and only failed a couple test cases from one of the input files provided.

The NFA class provided to actually construct the NFA was helpful, but I feel that the interface isn't very
good. The salient point being that adding characters to an alphabet requires passing in a set, that I would
imagine then get added, one by one, to the alphabet of the NFA. Since we're parsing characters one at a time,
why not provide a function that lets you add a single character to the alphabet? Or why not include both
to not inhibit the implementation? More so an annoyance than an actual obstacle. Just meant that boiler plate
code was needed when a cleaner interface would have been better.

## Testing

Sample input files along with expected output were provided by the instructor of the class. Three input files
were provided. Program correctness was verified by running this implementation on each input file
and examining the output. No additional input files were created. Nested precedence operators aren't
working at the moment and are hit or miss.
