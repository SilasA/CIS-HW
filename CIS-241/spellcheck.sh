# spell checker

# check if arg count is 2
if [ $# -ne 2 ]
then
    echo "Usage..."
    exit 1
fi

# check if args are readable
if [ ! -r $1 -o ! -r $2 ]
then
    echo "$1 or $2 is not readable"
    exit 2
fi

hunspell -l $2 | sort | uniq > zz

while read word
do
    echo word
done < zz
