const int sensorPin1 = A0;
const int sensorPin2 = A1;
const int sensorPin3 = A2;

const String sensorID1 = "0000";
const String sensorID2 = "0001";
const String sensorID3 = "0010";

int sensorState1 = 0;
int sensorState2 = 0;
int sensorState3 = 0;

void setup() {
  Serial.begin(9600);
  sensorState1 = analogRead(sensorPin1);
  sensorState2 = analogRead(sensorPin2);
  sensorState3 = analogRead(sensorPin3);
}

void loop() {
  int sensorValue1 = analogRead(sensorPin1);
  int sensorValue2 = analogRead(sensorPin2);
  int sensorValue3 = analogRead(sensorPin3);
  //Serial.println(sensorValue1);
  sensorState1 = compareState(sensorValue1, sensorState1, sensorID1);
  sensorState2 = compareState(sensorValue2, sensorState2, sensorID2);
  sensorState3 = compareState(sensorValue3, sensorState3, sensorID3);
  delay(500);
}

int compareState(int x, int y, String ID) {
  boolean state;
  int dif = y - x;
  //Serial.println("x:"+ String(x)+" y:"+ String(y)+" dif:"+String(dif));
  if ((dif > 200) && (x != y)) {
      state = true;
      transmit(ID, state); 
  } else if ((dif < -200) && (x != y)) {
      state = false;
      transmit(ID, state);
  }
  return x;
}

void transmit(String ID, boolean state) {
  Serial.println(ID + ":" + state);
}
