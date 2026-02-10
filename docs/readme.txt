TopSecret documentation

User Interface:
Parses arguments and determines how to call Program Control.
If there are no arguments, request a list of available files from Program Control.
If there are one or two arguments, pass them along to Program Control as the number of a file and the cipher to use respectively.

File Handler:
Receives either a filename or a filename with a cipher from Program Control.
Read the contents of the file.
If there is a cipher, send the file contents and the cipher to Cipher, receiving back the deciphered contents.
Give the file contents to Program Control.

Program Control:
If no arguments from User Interface, return back a list of files associated with a number.
If one or two arguments from User Interface, get the filename from the number (the first argument) and pass that along with the cipher (the second argument) to File Handler.

Cipher:
Receive file contents and cipher from File Handler.
Decrypt file contents.
Return file contents to File Handler.