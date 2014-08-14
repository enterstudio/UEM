#include "XC866.h"
#include "stdio.h"
#include "Serial.h"

void delay(void)		// Simple Delay between LED toggle and UART messages
{
	long int i=0;
	for(i=0;i<30000;i++);
}

void main(void)
{

	//char xdata stringy[3];
	unsigned int count = 0;
	unsigned int k;
	//int c = 0;

	char xdata row[8] = "11010011";
	char xdata msg[30];
	//P3_DIR = 0xff;
	UART_Init();
	//INTERRUPTS TO DO

	/*Menu for debugging
	sprintf(msg,"\rMenu\n"); //display a menu
	Sout(msg);

	sprintf(msg,"\rSelect 1 for Init\n",k);
	Sout(msg);

	sprintf(msg,"\rSelect 2 for Reset\n",k);
	Sout(msg);
	*/

	k =1;

	while(1)
	{
		sprintf(msg,"\rRow 1\n",k);
		Sout(msg);
		Sout(row);

		delay();
	}

}

