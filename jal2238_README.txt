COMS W4701 - Artificial Intelligence 
Jonathan Voris

Josh Lieberman
jal2238
Assignment 4 - Inference Algorithms

Programming Language Used: JavaSE-1.6
Development Environment Used: Eclipse Kepler (Build id: 20130614-0229)
Code tested on these environments: Eclipse, CLIC Lab Machines

Usage:
Run ./install, then ./entail <forward or backward> <knowledge base file> <symbol>.

KB.txt
P => Q
L ^ M => P
B ^ L => M
A ^ P => L
A ^ B => L
A
B

jal2238@prague:~/cs4701/assignment4$ ll
total 24
drwx------ 2 jal2238 student  4096 Dec 10 21:23 ./
drwx------ 6 jal2238 student  4096 Dec  9 18:03 ../
-rwxr-xr-x 1 jal2238 student    37 Dec 10 11:14 entail*
-rw------- 1 jal2238 student 13858 Dec 10 21:23 EntailmentTester.java
-rwxr-xr-x 1 jal2238 student    25 Dec  9 18:31 install*
-rw------- 1 jal2238 student    55 Dec 10 21:11 KB.txt
jal2238@prague:~/cs4701/assignment4$ ./install
jal2238@prague:~/cs4701/assignment4$ ll
total 32
drwx------ 2 jal2238 student  4096 Dec 10 21:24 ./
drwx------ 6 jal2238 student  4096 Dec  9 18:03 ../
-rwxr-xr-x 1 jal2238 student    37 Dec 10 11:14 entail*
-rw------- 1 jal2238 student  5641 Dec 10 21:24 EntailmentTester.class
-rw------- 1 jal2238 student 13858 Dec 10 21:23 EntailmentTester.java
-rwxr-xr-x 1 jal2238 student    25 Dec  9 18:31 install*
-rw------- 1 jal2238 student    55 Dec 10 21:11 KB.txt
jal2238@prague:~/cs4701/assignment4$ ./entail forward KB.txt Q
B
A
A ^ B => L
L
B ^ L => M
A ^ P => L
L
M
L ^ M => P
P
P => Q
--> true
jal2238@prague:~/cs4701/assignment4$

Note: In addition to Professor Voris' slides and the textbook, I used these
outside resources to help writing my forward and backward chaining algorithims:
http://www-scf.usc.edu/~csci460/docs/lecture/lec16.pdf
http://www.cs.sunysb.edu/~leortiz/teaching/6.034f/Fall05/rules/fwd_bck.pdf
http://www-bcf.usc.edu/~macskass/CS561/Spring2011/slides/Lecture-10-11-Logical_Agents.pdf

Sample Knowledge Bases:
(All test runs can be found in their jal2238_KB#_test.txt files respectively)

KB 1 (used to test forward chaining):
SLEEPY => GO_TO_BED
STAY_UP_LATE => SLEEPY
PLAY_GAMES ^ STAY_UP_LATE => SLEEPY
PLAY_GAMES => FUN
I => PLAY_GAMES
I

SLEEPY yeilds GO_TO_BED (If I'm sleepy, I go to bed)
STAY_UP_LATE yeilds SLEEPY (If I stay up late, I'll be sleepy)
PLAY_GAMES and STAY_UP_LATE yeilds SLEEPY (If I play games and stay up late, I'll be sleepy)
PLAY_GAMES yeilds FUN (If I play games, I'll have fun)
I yeilds PLAY_GAMES (If I is true, I'm playing games)
I (given I, or I is true)

KB 2 (used to test forward chaining):
EGG ^ COOK => FOOD
EGG ^ GREEN => SPOILED
EGG ^ SPOILED ^ COOK => SICK
EGG ^ KITCHEN => COOK
EGG
KITCHEN

EGG and COOK yeilds FOOD (If I cook the egg, I have food)
EGG and GREEN yeilds SPOILED (If the egg is green, it is spoiled)
EGG and SPOILED and COOK yeilds SICK (If the egg is spoiled and I cook it, I will be sick)
EGG and KITCHEN yeilds COOK (If I have an egg and a kitchen, I'll cook)
EGG (given I have an egg, or egg is true)
KITCHEN (given I have a kitchen, or kitchen is true)

KB 3 (used for backward chaining):
DOUGH ^ CHEESE ^ SAUCE => PIZZA
LETTUCE

DOUGH and CHEESE and SAUCE yeilds PIZZA (If I have dough and cheese and sauce, I have a pizza)
LETTUCE (given lettuce, or lettuce is true)

KB 4 (used for backward chaining):
COMPUTER ^ WINDOWS ^ PORTABLE => WINDOWS_LAPTOP
COMPUTER ^ PORTABLE ^ MAC => MACBOOK
COMPUTER ^ PORTABLE => LAPTOP
COMPUTER ^ APPLE => MAC
COMPUTER
APPLE

COMPUTER and WINDOWS and PORTABLE yeilds WINDOWS_LAPTOP (If a computer is portable and runs Windows, then it is a windows laptop)
COMPUTER and PORTABLE and MAC yeilds MACBOOK (If a computer is portable and a Mac, then it is a MacBook)
COMPUTER and PORTABLE yeilds LAPTOP (If a computer is portable, then it is a laptop)
COMPUTER and APPLE yeilds MAC (If a computer is an Apple, then it is a Mac)
COMPUTER
APPLE