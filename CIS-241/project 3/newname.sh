#!/usr/bin

end=0
file=$2

oldIFS=$IFS
IFS='.'
split=($2)

if [ "$#" -ne 2 ]; then
    echo "Invalid Arguements"
    exit 1
fi

if [ ! -f "$1" ]; then
    echo "$1 does not exist"
    exit 1
fi

while [ -f "$file" ]
do
    ((end++))
    file="${split[0]}$end.${split[1]}"
done

mv "./$1" "./$file"

IFS=$oldIFS
