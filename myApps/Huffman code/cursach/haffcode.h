#ifndef HAFFCODE_H
#define HAFFCODE_H

#include <QMainWindow>
#include <QObject>
#include <QWidget>
#include <map>
#include <vector>
#include <utility>
#include <QGraphicsScene>
#include <QGraphicsItem>
#include <QGraphicsView>

using namespace std;

class haffCode
{
private:
    vector <pair<QString, int>> myVector;
    typedef struct element{
        int weight;
        QString sym;
        QString bicode;
        element* lt;
        element* rt;
        qreal x, y;
        element* parent;
        element(){
            lt = nullptr;
            rt = nullptr;
            parent = nullptr;
        }
    }element;
    vector <element*> *nodeVector = new vector<element*>;
    QString newText;
public:
    QGraphicsScene *haffScene = new QGraphicsScene;
    void painteredo(element *t, bool flag);
    haffCode();
    QString code(QString text);
    void free(element* parent);
    element* buildTree(vector <pair<QString, int>> newVector);
    element* make_node(element* lt, element* rt);
    void node_sort(vector <element*> *nodeVec);
    void setBinCode(element* Root, vector <pair<QString, QString>> *resVector, int i);
};

#endif // HAFFCODE_H
