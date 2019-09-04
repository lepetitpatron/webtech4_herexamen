from django.urls import path
from . import views

app_name = 'infractions'

urlpatterns = [
    path('infractions/', views.infractions, name='infractions'),
]
