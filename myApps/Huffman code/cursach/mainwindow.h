#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "haffcode.h"
#include "form.h"
#include <QGraphicsScene>
#include <QGraphicsItem>
#include <QGraphicsView>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_haffButton_clicked();

    void on_read1Button_clicked();

    void on_write1Button_clicked();

    void on_SFCodeButton_clicked();

    void on_decodeButton_clicked();

    void on_visualizeButton_clicked();

    void on_help_Button_clicked();

private:
    QString  text;
    QGraphicsScene *scene;
    Ui::MainWindow *ui;
    QString readfile;
    QString writefile;
};
#endif // MAINWINDOW_H
