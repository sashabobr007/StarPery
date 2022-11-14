# запускаем скрипт на удаленном сервере
# с помощью ngrok создаем туннель
# на данный момент url = https://8c6a-62-113-109-24.eu.ngrok.io

#update with PyCharm

#commit and push


from flask import *
import json
from qiwi_api_qr import *
from db import *
import uuid

app = Flask(__name__)

@app.route('/', methods=['POST'])
def main_user():
    data_set = {'api': 'success'}
    json_data = json.dumps(data_set)
    return json_data

@app.route('/insert/phone/', methods=['POST'])
def main_request_insert():
    input_json = request.get_json(force=True)
    phone = str(input_json['phone'])
    print(phone)
    try:

        db = Database()
        db.new_user(phone)

        responce = 'success'
    except:
        responce = 'error'
    data_set = {'insert_new_user': responce}
    json_data = json.dumps(data_set)
    return json_data

@app.route('/token_create/phone/', methods=['POST'])
def token_create():
    input_json = request.get_json(force=True)
    phone = str(input_json['phone'])
    try:
        accountid = uuid.uuid4()
        requestedid = uuid.uuid4()
        try:
            create_token(str(requestedid), str(accountid), phone)
            data_set = {'accountid': f'{str(accountid)}',
                        'requestedid' : f'{str(requestedid)}'}
            json_data = json.dumps(data_set)
        except:
            data_set = {'error': 'qiwi_error'}
            json_data = json.dumps(data_set)
    except:
        data_set = {'error': 'db_error'}
        json_data = json.dumps(data_set)
    return json_data

@app.route('/token_cms/phone/requestedid/', methods=['POST'])
def token_cms():
    input_json = request.get_json(force=True)
    phone = str(input_json['phone'])
    requestid = str(input_json['requestedid'])
    try:
        json_data = cms(requestid)
        try:
            token = json_data['token']['value']
            db = Database()
            db.new_trans(phone, token)
        except:
            data_set = {'error': 'db_error'}
            json_data = json.dumps(data_set)
    except:
        data_set = {'error': 'qiwi_error'}
        json_data = json.dumps(data_set)
    return json_data

@app.route('/token_pay/paymentid/paymenttoken/accountid/amount/', methods=['POST'])
def token_pay():
    db = Database()
    paymentid = str(uuid.uuid4())
    input_json = request.get_json(force=True)
    paymenttoken = str(input_json['paymenttoken'])
    accountid = str(input_json['accountid'])
    amount = str(input_json['amount'])
    try:
        json_data = pay(paymentid, paymenttoken,  accountid, amount)
        db.update_trans(paymenttoken)
    except:
        data_set = {'error': 'qiwi_error'}
        json_data = json.dumps(data_set)
    return json_data

if __name__ == '__main__':
    app.run(port=7777)