#include "haffcode.h"
#include <map>
#include <vector>
#include <utility>
#include "mainwindow.h"

void haffCode::painteredo(element *t, bool flag){
    if (t==nullptr) return;
    int of = t->sym.length() - 1;
    if(flag)
        haffScene->addEllipse(t->x,t->y,50+of*7,50,QPen(Qt::black),QBrush(Qt::white));
    else
        haffScene->addEllipse(t->x,t->y,50+of*7,50,QPen(Qt::green),QBrush(Qt::white));
    QGraphicsTextItem *textItem = new QGraphicsTextItem(t->sym);
    textItem->setPos(t->x+18,t->y+15);
    haffScene->addItem(textItem);
    if(t->lt)
    {
        int of1 = t->lt->sym.length() - 1;
        haffScene->addLine(t->x+25+of*3.5, t->y+50, t->lt->x+25+of1*3.5, t->lt->y,QPen(Qt::black));
        painteredo(t->lt, flag);
    }
    if(t->rt)
    {
        int of1 = t->rt->sym.length() - 1;
        haffScene->addLine(t->x+25+of*3.5, t->y+50, t->rt->x+25+of1*3.5, t->rt->y,QPen(Qt::black));
        painteredo(t->rt, flag);
    }
}

haffCode::haffCode()
{

}

void haffCode::setBinCode(element* Root, vector <pair<QString, QString>> *resVector, int i){

    if(Root->lt){
        Root->lt->bicode = Root->bicode + "0";
        Root->lt->x = Root->x - i*0.8;
        Root->lt->y = Root->y + 75;
        setBinCode(Root->lt, resVector, i - 25);
    }
    if(Root->rt){
        Root->rt->bicode = Root->bicode + "1";
        Root->rt->x = Root->x + i*0.8;
        Root->rt->y = Root->y + 75;
        setBinCode(Root->rt, resVector, i - 25);
    }
    if (!Root->lt && !Root->rt){
        resVector->push_back(make_pair(Root->sym, Root->bicode));
    }
    return;
}

vector <pair<QString, int>> sort(vector <pair<QString, int>> newVector){  // Это надо протестить

    vector <pair<QString, int>> tmpVector;
    tmpVector.push_back(make_pair("r", 15));
    for (unsigned long long i = 0; i< newVector.size()-1; i++){
        for (unsigned long long j = 1; j< newVector.size(); j++){
            if(newVector[j-1].second < newVector[j].second){
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

void haffCode::node_sort(vector <element*> *nodeVec){
    element* tmp = new element;
    for (unsigned long long i = 0; i < nodeVec->size()-1; i++){
        for (unsigned long long j = 1; j < nodeVec->size(); j++){
            if ((*nodeVec)[j-1]->weight < (*nodeVec)[j]->weight){
                tmp = (*nodeVec)[j-1];
                (*nodeVec)[j-1] = (*nodeVec)[j];
                (*nodeVec)[j] = tmp;
            }
        }
    }
}

haffCode::element* haffCode::make_node(element *lt, element *rt){  // Справа - больший элемент, слева - меньший.
    element* parent = new element;    
    parent->sym = lt->sym + rt->sym;
    parent->weight = lt->weight + rt->weight;
    parent->rt = rt;
    parent->lt = lt;
    lt->parent = parent;
    rt->parent = parent;
    return parent;
}

haffCode::element* haffCode::buildTree(vector <pair<QString, int>> newVector){
    newVector = sort(newVector);
    for (unsigned long long i = 0; i < newVector.size(); i++){
        element *tmp = new element;
        tmp->weight = newVector[i].second;
        tmp->sym = newVector[i].first;
        tmp->lt = nullptr;
        tmp->rt = nullptr;
        tmp->parent = nullptr;
        (*nodeVector).push_back(tmp);
    }
    while (newVector.size() > 1){
        pair <QString, int> pair1;
        pair <QString, int> pair2;
        newVector = sort(newVector);
        node_sort(nodeVector);
        unsigned long long nodeVecSize = (*nodeVector).size();
        element* rt = new element;
        element* lt = new element;
        element *parent = new element;
        lt = (*nodeVector)[nodeVecSize-1];
        rt = (*nodeVector)[nodeVecSize-2];
        parent = make_node(lt, rt);
        if (newVector.size() >= 2){
            newVector.pop_back();
            newVector.pop_back();
            (*nodeVector).pop_back();
            (*nodeVector).pop_back();
        }
        newVector.push_back(make_pair(parent->sym, parent->weight));
        (*nodeVector).push_back(parent);
        if (newVector.size() == 1){
            return parent;
        }
    }

}

QString haffCode::code(QString text){
    vector <pair<QString, QString>> *resVector = new vector <pair<QString, QString>>;
    vector <pair<QString, QString>> new_res_vector;
    QString res = "";
    QString s = "";
    s = text[0];
    myVector.push_back(make_pair(s, 1));
    for (int i = 1; i<text.length(); i++){
        s = "";
        s = text[i];
        int flag = 0;
        unsigned long long vectorSize = myVector.size();
        if(vectorSize > 1){
            for (unsigned long long j = 0; j < vectorSize; j++){
                if (s == myVector[j].first){
                    myVector[j].second++;
                    flag = 1;
                }
                if (!flag && j == vectorSize-1){
                    myVector.push_back(make_pair(s, 1));
                }
            }
        }
        else {
            if (s == myVector[0].first){
                myVector[0].second++;
            }
            else {
                myVector.push_back(make_pair(s, 1));
            }
        }
    }
    element* myTree = new element;
    myTree = buildTree(myVector);
    myTree->x = 250;
    myTree->y = 0;
    // пробежка по бинарному дереву и присвоение каждому элементу его двоичного кода
    setBinCode(myTree, resVector, 200);
    // создание вектора пар с символами и их двоичным представлением
    painteredo(myTree, false);
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

