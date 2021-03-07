#include "form.h"
#include "ui_form.h"

Form::Form(QGraphicsScene *scene,QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Form)
{
    ui->setupUi(this);
    ui->graphicsView->setScene(scene);
}

Form::~Form()
{
    delete ui;
}
