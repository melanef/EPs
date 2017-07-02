/* KMP.h */

#include "List.h"
#include "String.h"

#ifndef NEF_KMP
#define NEF_KMP 1

int * KMPFailureFunction(String * word);

List * KMPMatch(String * text, String * word);

#endif
