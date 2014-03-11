from django.db import models

class Bar(models.Model):
    name = models.CharField(max_length=20, unique=True)

    def __unicode__(self):
        return self.name

class Foo(models.Model):
    name = models.CharField(max_length=20, unique=True)
    bar = models.ForeignKey(Bar)

    def __unicode__(self):
        return u'%s %s' % (self.bar, self.name)
