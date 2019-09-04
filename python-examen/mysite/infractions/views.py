from django.shortcuts import render
import os, json

# Create your views here.
def infractions(req):
        with open( os.path.dirname(os.path.realpath(__file__)) + '\infractions.json', 'r') as json_file:
                data = json.load(json_file)

        results = []
        for r in data:
                results.append(r)

        print(results)
        return render(req, 'result.html', {'infractions': results}) 


