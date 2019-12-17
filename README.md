# Computer-Networking
At startup, the client prompts for a string S from the user.

The client then splits the string S, assumed to be of length n, into two strings S1 and S2 that are of equal length n/2 if n is even, or of lengths (n + 1)/2 and (n − 1)/2 respectively if n is odd.

The client then:
  1. sends S1 and S2 to the server in two distinct UDP packets;
  2. waits for a string R from the server, in another UDP packet;
  3. checks if R is equal to S inverted;
  4. displays the result of the final test to the user.

The server constantly waits for UDP messages.
The server waits for a sequence of two distinct UDP messages containing strings S1 and S2.

The server then:
  1. concatenates S1 and S2;
  2. inverts the result of the concatenation, obtaining R;
  3. sends R to the client as a UDP packet;
  4. starts again, awaiting a sequence of two messages.
