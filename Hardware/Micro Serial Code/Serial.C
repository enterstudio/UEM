#include "XC866.h"
#include "stdio.h"

void Serial_Init (void)
{
	P1_DIR = 0x02;			//P1.1 = Tx, P1.0 = Rx
	PORT_PAGE = 0x02;
	P1_ALTSEL0 = 0xFD;		// Port1 as Serial comm out
	P1_ALTSEL1 = 0x02;		//
	PORT_PAGE = 0x00;
	BCON = 0x00;			// BR gen off
	SCON =0x50;				// Serial mode on and mode 1
	BG = 0xAE;				// BR = 9600

	BCON = 0x01;			// Serial mode on after update parameter
}

//func for sending serial message
void Sout(char * msg0)
{
	while (*msg0)
	{
		SBUF = *msg0++;

		while (TI==0){;} 	//wait while interrupted
		TI=0;
	}
}