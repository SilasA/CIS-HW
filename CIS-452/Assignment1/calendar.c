#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

const int month_days[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
const char* months[12] = { 
    "January", "February", "March",
    "April", "May", "June", "July",
    "August", "September", "October",
    "November", "December" 
};

int num_days(int month);
int is_leap(int year);

void calendar(struct tm* data);

int main(int argc, char** argv) {
    struct tm tempTime;
    time_t firstDayTime;

    if (argc == 3) {
        tempTime.tm_mon = atoi(argv[1]) - 1;
        tempTime.tm_year = atoi(argv[2])-1900;
        tempTime.tm_mday = 1;
        tempTime.tm_hour = 1;
        tempTime.tm_min = 1;
        tempTime.tm_sec = 1;
        tempTime.tm_isdst = -1;
    } else {
        time_t t;
        time(&t);
        struct tm *cur;
        cur = localtime(&t);
        tempTime = *cur;
        tempTime.tm_mday = 1;
    }

    firstDayTime = mktime(&tempTime);
    tempTime = *localtime(&firstDayTime);

    calendar(&tempTime);
    return 0;
}

int num_days(int month) {
    return month_days[month];
}

int is_leap(int year) {
   return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0); 
}

void calendar(struct tm* firstday) {
    int weekdayCounter = 0;
    char date[50];

    sprintf(date, "%s %d", months[firstday->tm_mon], firstday->tm_year+1900);   
    int pad = (20 - strlen(date)) / 2;

    for (int i = 0; i < pad; i++) printf(" ");
    printf("%s\n", date);
    
    printf("Su Mo Tu We Th Fr Sa\n");
    for (int i = 0; i < firstday->tm_wday; i++) {
        weekdayCounter++;
        printf("   ");
    }
    for (int i = 1; i <= month_days[firstday->tm_mon]; i++) {
        weekdayCounter++;
        printf("%2d ", i);
        if (weekdayCounter > 6) {
            weekdayCounter = 0;
            printf("\n");
        }
    }
    printf("\n");
}
