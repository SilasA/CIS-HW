#define SIZE 16*1024
#define NUM 100000

int main() {
    int array[SIZE];    
    register int outer = 0;
    register int something = 0;

    for (outer = 0; outer < NUM; outer++) {
        something = array[0];
        something = array[2];
        something = array[4];
        something = array[6];
        something = array[8];
        something = array[10];
        something = array[12];
        something = array[14];
        something = array[16];
    }

    return 0;
}
