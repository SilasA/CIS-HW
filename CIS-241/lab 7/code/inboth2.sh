# find out if a word is in both files

echo
if test $# -lt 3
then
   echo "usage: inboth word file1 file2"
   echo
   exit 0
fi

if grep -wq $1 $2 -a grep -wq $1 $3
then
   echo "word $1 found in both $2 and $3"
else
   echo "word $1 is not found in $2 or $3"
fi

echo
