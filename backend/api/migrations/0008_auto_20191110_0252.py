# Generated by Django 2.2.4 on 2019-11-10 02:52

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0007_league_game_released'),
    ]

    operations = [
        migrations.AddField(
            model_name='scrimmage',
            name='blue_mu',
            field=models.IntegerField(null=True),
        ),
        migrations.AddField(
            model_name='scrimmage',
            name='red_mu',
            field=models.IntegerField(null=True),
        ),
    ]
