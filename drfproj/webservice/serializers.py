from rest_framework import serializers
from webservice.models import Foo, Bar

class BarSerializer(serializers.ModelSerializer):
    class Meta:
        model = Bar

class FooSerializer(serializers.ModelSerializer):
    bar = BarSerializer(many=False)

    class Meta:
        model = Foo
