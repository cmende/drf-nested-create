from django.shortcuts import render
from rest_framework import viewsets
from webservice.models import Foo, Bar
from webservice.serializers import FooSerializer, BarSerializer

class FooViewSet(viewsets.ModelViewSet):
    model = Foo
    serializer_class = FooSerializer

class BarViewSet(viewsets.ModelViewSet):
    model = Bar
    serializer_class = BarSerializer
