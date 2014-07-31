
/P3 array of input ports, connected to sensors
//P1_1 interrupt output that main micro listens to
//P2_1 input from main micro to signal communication

boolean waiting; // if still waiting for main micro to sync
boolean ports[]; //local representation of parking bays sensors

ports = P3;

while(1)//continious main loop
{
	if (ports != P3 || waiting)//Then state has changed, so signal main micro that change occured
	{
		ports = P3; //update local rep
		P1_1 = 1; //Signal main micro
		waiting = true;
		while (P2_1 = 0){;} //Wait for main micro to respond on P2

		while (P2_1 = 1)//add error checking, add boolean sucess
		{
			sync();  //use serial comm to sync ports
			//after a successful sync...
			P1_1 = 0;
			waiting = false;
		}

		wait(10000);//timer to slow down speed of iterations
	}
}

wait(int num)
{
	for int (k = 0; k < num; k++)
}

//Add check and update of ports vs. P3 if P3 changes during 'waiting'