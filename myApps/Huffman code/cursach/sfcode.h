#ifndef SFCODE_H
#define SFCODE_H

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

class SFCode
{
private:
    vector <pair<QString, int>> sfVector;
    typedef struct element{
        int weight;
        QString sym;
        qreal x = 250;
        qreal y = 0;
        QString bicode;
        element* lt;
        element* rt;
        element* parent;
        element(){
            lt = nullptr;
            rt = nullptr;
            parent = nullptr;
        }
    }element;
    vector <element*> *nodeSFVector = new vector<element*>;
public:
    void painteredo(element *t, bool flag);
    QGraphicsScene *SFscene = new QGraphicsScene;
    SFCode();
    QString code(QString text);
    void free(element* parent);
    int lMiddle (vector <pair<QString, int>> SFVector);
    void make_nodes(vector <element*> *nodes_vector, vector <pair<QString, int>> newVector, int i);
    vector <pair<QString, int>> SFsort(vector <pair<QString, int>> newVector);
};

#endif // SFCODE_H
