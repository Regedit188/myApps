#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "haffcode.h"
#include "sfcode.h"
#include "decode.h"
#include <QTextStream>
#include <QFile>
#include <QFileDialog>
#include <QMessageBox>
#include <QGraphicsView>
#include <QValidator>
#include "help.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    scene = new  QGraphicsScene (this);
}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_haffButton_clicked()
{
    haffCode obj;
    scene = obj.haffScene;
    text = ui->textEdit->toPlainText();
    text = obj.code(text);
    ui->textEdit_2->setPlainText(text);
   //ui->textEdit_2->setText(text);
}

void MainWindow::on_read1Button_clicked()
{
    readfile = QFileDialog::getOpenFileName(0, "Выберете файл", "D:/", "*.txt");
    if(readfile == "")
    {
    ui->statusbar->showMessage("Файл не выбран.");
    return;
    }
    QFile openFile(readfile);
    if(!openFile.open(QFile::ReadOnly | QFile::Text))
    {
    QMessageBox::information(this, "Ошибка", "Файл для чтения не удалось открыть!");
    ui->statusbar->showMessage("Ошибка");
    return;
    }
    QTextStream stream(&openFile);
    QString buffer = stream.readAll();
    ui->textEdit->setText(buffer);
    openFile.flush();
    openFile.close();
}

void MainWindow::on_write1Button_clicked()
{
    writefile = QFileDialog::getSaveFileName(nullptr, "Выберете файл для записи",  "D:/", "*.txt");
    QFile saveFile(writefile);
    if(!saveFile.open(QFile::WriteOnly | QFile::Text))
    {
        QMessageBox::information(this, "Ошибка", "Файл для записи не удалось открыть!");
        return;
    }
    QTextStream stream2(&saveFile);
    stream2 << text;
    saveFile.close();
}

void MainWindow::on_SFCodeButton_clicked()
{
    SFCode obj;
    text = ui->textEdit->toPlainText();
    scene = obj.SFscene;
    text = obj.code(text);
    ui->textEdit_2->setPlainText(text);

}



void MainWindow::on_decodeButton_clicked()
{
    decode obj;
    QString text = ui->textEdit->toPlainText();
    text = obj.decoding(text);
    ui->textEdit_2->setPlainText(text);
}



void MainWindow::on_visualizeButton_clicked()
{
    Form* win = new Form(scene);
    win->show();
}



void MainWindow::on_help_Button_clicked()
{
    HelpBrowser* helphtml = new HelpBrowser("D:\\", "help4.html");
    helphtml->resize(800, 600);
    helphtml->show();
}




