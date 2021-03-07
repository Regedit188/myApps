#include "decode.h"
#include <vector>
#include <utility>
#include <map>
using namespace std;

decode::decode()
{

}


QString decode::decoding(QString str){
    vector <pair<QString, QString>> resVector;
    QString sym = "";
    QString bicode = "";
    int i = 1;
    while(i < str.length()){
        sym = str[i-1];
        sym += str[i];
        if(sym == "\n\n"){
            i++;
            break;
        }
        i++;
    }
    while(i < str.length()){
        sym = str[i];
        i += 4;
        while(str[i] != "\n"){
            if(i >= str.length()){
                break;
            }
            bicode += str[i];
            i++;
        }
        resVector.push_back(make_pair(sym, bicode));
        bicode = "";
        i++;
    }
    QString resStr = "";
    QString checkStr = "";
    for (int i = 0; i < str.length(); i++){
        checkStr += str[i];
        for (unsigned long long j = 0; j < resVector.size(); j++){
            if (checkStr == resVector[j].second){
                resStr += resVector[j].first;
                checkStr = "";
            }
        }
        if (str[i] == "\n" && str[i+1] == "\n"){
            break;
        }
    }
    return resStr;
}
