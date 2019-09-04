from django.shortcuts import render
import os, json

# Create your views here.
def infractions(req, speed):
        with open( os.path.dirname(os.path.realpath(__file__)) + '\infractions.json', 'r') as json_file:
                data = json.load(json_file)

        results = []
        for r in data:
                if r['infractions_speed'] >= speed:
                        results.append(r)

        results.sort(key=lambda s:s['infractions_speed'])
        return render(req, 'result.html', {'infractions': results}) 


