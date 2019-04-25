#include <stdio.h>

int main(){
	char ch;
	// scanf("%c", &ch);
	while ( ch = getchar(), ch != EOF ) {
		printf("%c\n", ch);
		// scanf("%c", &ch);
	}
	return 0;
}
