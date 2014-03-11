from rest_framework.routers import DefaultRouter
from webservice import views

router = DefaultRouter()
router.register('foos', views.FooViewSet)
router.register('bars', views.BarViewSet)

urlpatterns = router.urls
