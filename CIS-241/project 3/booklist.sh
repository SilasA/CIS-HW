# !/usr/bin

oldIFS=$IFS
IFS=':'

if [ "$#" -ne 1 ]; then
    echo "Invalid Arguments"
    exit 1
fi

if [ ! -f "$1" -o ! -r "$1" -o ! -w "$1" ]; then
    echo "File is unusable"
    exit 1
fi


state=0

while [ $state -ne 1 ] 
do
    echo "[S]earch"
    echo "[A]dd"
    echo "D[e]lete"
    echo "[D]isplay"
    echo "[Q]uit"

    read input

    if [ $input = "S" -o $input = "s" ]; then
        echo "Enter an author:"
        read auth
        awk -F: -v a="$auth" '{ if ($1 == a) { printf "%s\t%s\t%s\n", $1, $2, $3 } }' "$1"
    fi
    if [ $input = "A" -o $input = "a" ]; then
        dat=("","","")
        echo "Enter Author:"
        read dat[0]
        echo "Enter Title:"
        read dat[1]
        echo "Enter Year:"
        read dat[2]
        echo "${dat[0]}:${dat[1]}:${dat[2]}" >> "$1"
    fi
    if [ $input = "E" -o $input = 'e' ]; then
        echo "Enter Title:"
        read title
        echo $title
        awk -F: -i inplace "{ if (\$2 != "$title") {print} }" "$1" > "$1"
    fi
    if [ $input = 'D' -o $input = 'd' ]; then
        echo "Entries: $(wc -l "$1")"
        awk -F: '{ printf "%s\t%s\t%s\n", $1, $2, $3 }' "$1"
    fi
    if [ $input = 'Q' -o $input = 'q' ]; then
        state=1
    fi
done


IFS=$oldIFS
