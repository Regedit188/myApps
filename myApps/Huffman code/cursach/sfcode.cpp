#include "sfcode.h"
#include <map>
#include <vector>
#include <utility>

SFCode::SFCode()
{

}

void SFCode::painteredo(element *t, bool flag){
    if (t==nullptr) return;
    int of = t->sym.length() - 1;
    if(flag)
        SFscene->addEllipse(t->x,t->y,50+of*7,50,QPen(Qt::black),QBrush(Qt::white));
    else
        SFscene->addEllipse(t->x,t->y,50+of*7,50,QPen(Qt::green),QBrush(Qt::white));
    QGraphicsTextItem *textItem = new QGraphicsTextItem(t->sym);
    textItem->setPos(t->x+18,t->y+15);
    SFscene->addItem(textItem);
    if(t->lt)
    {
        int of1 = t->lt->sym.length() - 1;
        SFscene->addLine(t->x+25+of*3.5, t->y+50, t->lt->x+25+of1*3.5, t->lt->y,QPen(Qt::black));
        painteredo(t->lt, flag);
    }
    if(t->rt)
    {
        int of1 = t->rt->sym.length() - 1;
        SFscene->addLine(t->x+25+of*3.5, t->y+50, t->rt->x+25+of1*3.5, t->rt->y,QPen(Qt::black));
        painteredo(t->rt, flag);
    }
}
int SFCode::lMiddle (vector <pair<QString, int>> SFVector){
    int midSum = 0;
    for (unsigned long long i = 0; i<SFVector.size(); i++){
        midSum += SFVector[i].second;
    }
    midSum /= 2;
    int midIndex = 0;
    int currentSum = 0;
    if (SFVector.size() > 2){
        while(currentSum < midSum){
            currentSum += SFVector[midIndex].second;
            if (currentSum == midSum){
                break;
            }
            if (currentSum > midSum){
                midIndex--;
                break;
            }
            midIndex++;
        }
    }
    if (SFVector.size() == 2){
        return 0;
    }
    return midIndex;
}


vector <pair<QString, int>> SFCode::SFsort(vector <pair<QString, int>> newVector){

    vector <pair<QString, int>> tmpVector;
    tmpVector.push_back(make_pair("r", 15));
    for (unsigned long long i = 0; i< newVector.size()-1; i++){
        for (unsigned long long j = 1; j< newVector.size(); j++){
            if(newVector[j-1].second > newVector[j].second){
                tmpVector[0].first = newVector[j-1].first;
                tmpVector[0].second = newVector[j-1].second;
                newVector[j-1] = newVector[j];
                newVector[j].first = tmpVector[0].first;
                newVector[j].second = tmpVector[0].second;
            }
        }
    }
    return newVector;

}

void SFCode::make_nodes(vector <element*> *nodes_vector, vector <pair<QString, int>> newVector, int i){

    if (newVector.size() > 1){
        element* tmp3 = new element;
        unsigned long long sizeVec = (*nodes_vector).size();
        unsigned long long mid = lMiddle(newVector);
        tmp3->sym = "";
        tmp3->weight = 0;
        vector <pair<QString, int>> newLeftVector;
        for (unsigned long long i = 0; i <= mid; i++){
            tmp3->sym += newVector[i].first;
            tmp3->weight += newVector[i].second;
            newLeftVector.push_back(newVector[i]);
        }
        tmp3->bicode = (*nodes_vector)[sizeVec-1]->bicode + "0";        // Проработать для последнего оставшегося символа и придумать как это будет переходить слева направо
        if ((*nodes_vector)[sizeVec-1]){
            (*nodes_vector)[sizeVec-1]->lt = tmp3;
            tmp3->parent = (*nodes_vector)[sizeVec-1];
            tmp3->x = tmp3->parent->x - i*0.8;
            tmp3->y = tmp3->parent->y + 75;
        }
        (*nodes_vector).push_back(tmp3);
        make_nodes(nodes_vector, newLeftVector, i - 20);
        element* tmp1 = new element;
        tmp1->sym = "";
        tmp1->weight = 0;
        vector <pair<QString, int>> newRightVector;
        for (unsigned long long j = mid+1; j < newVector.size(); j++){
            tmp1->sym += newVector[j].first;
            tmp1->weight += newVector[j].second;
            newRightVector.push_back(newVector[j]);
        }
        tmp1->bicode = (*nodes_vector)[sizeVec-1]->bicode + "1";
        if ((*nodes_vector)[sizeVec-1]){
            (*nodes_vector)[sizeVec-1]->rt = tmp1;
            tmp1->parent = (*nodes_vector)[sizeVec-1];
            tmp1->x = tmp1->parent->x + i*0.8;
            tmp1->y = tmp1->parent->y + 75;
        }
        (*nodes_vector).push_back(tmp1);
        make_nodes(nodes_vector, newRightVector, i - 20);
    }
    if(newVector.size() == 1){
        return;
    }

}

QString SFCode::code(QString text){
    vector <pair<QString, QString>> *resVector = new vector <pair<QString, QString>>;
    QString res = "";
    QString s = "";
    s = text[0];
    sfVector.push_back(make_pair(s, 1));
    for (int i = 1; i<text.length(); i++){
        s = "";
        s = text[i];
        int flag = 0;
        unsigned long long vectorSize = sfVector.size();
        if(vectorSize > 1){
            for (unsigned long long j = 0; j < vectorSize; j++){
                if (s == sfVector[j].first){
                   sfVector[j].second++;
                    flag = 1;
                }
                if (!flag && j == vectorSize-1){
                    sfVector.push_back(make_pair(s, 1));
                }
            }
        }
        else {
            if (s == sfVector[0].first){
                sfVector[0].second++;
            }
            else {
                sfVector.push_back(make_pair(s, 1));
            }
        }
    }
    sfVector = SFsort(sfVector);
    element* tmp = new element;
    tmp->sym = "";
    tmp->weight = 0;
    tmp->bicode = "";
    for (unsigned long long i = 0; i < sfVector.size(); i++){
        tmp->sym += sfVector[i].first;
        tmp->weight += sfVector[i].second;
    }
    (*nodeSFVector).push_back(tmp);
    make_nodes(nodeSFVector, sfVector, 200);
    painteredo((*nodeSFVector)[0], false);
    unsigned long long nodVecSize = (*nodeSFVector).size();
    for(unsigned long long o = 0; o < nodVecSize; o++){
        if((*nodeSFVector)[o]->sym.length() == 1){
           (*resVector).push_back(make_pair((*nodeSFVector)[o]->sym, (*nodeSFVector)[o]->bicode));
        }
    }
    for (int k = 0; k < text.length(); k++){
        for (unsigned long long l = 0; l < resVector->size(); l++){
            if (text[k] == (*resVector)[l].first){
                res += (*resVector)[l].second;
            }
        }
    }
    res += "\n\n";
    for (unsigned long long m = 0; m < resVector->size(); m++){
        res += (*resVector)[m].first + "-->" + (*resVector)[m].second;
        res += "\n";
    }

    return res;
}
