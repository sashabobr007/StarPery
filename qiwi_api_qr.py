import requests
import json
siteId = 'sa3khn-14'
merchant_token = '7b78feb1-d81e-44c5-9f08-9ad80eb5f032'

def create_token(requestid, accountid, phone) -> json:
    headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {merchant_token}',
    }
    data = {
        "requestId": requestid,
        "phone": phone,
        "accountId": accountid
    }
    response = requests.post(f'https://api.qiwi.com/partner/payin-tokenization-api/v1/sites/{siteId}/token-requests',
                             json=data, headers=headers).json()
    response = json.dumps(response)
    return response

def cms(requestid) -> json:
    headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {merchant_token}',
    }
    data = {
        "requestId": requestid,
        "smsCode": "1276"
    }
    response = requests.post(f'https://api.qiwi.com/partner/payin-tokenization-api/v1/sites/{siteId}/token-requests/complete',
                             json=data, headers=headers).json()
    #response = json.dumps(response)
    return response

def pay(paymentid, paymenttoken, accountid, amount) -> json:
    amount_new = f'{str(amount)}.00'
    headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {merchant_token}',
    }
    data = {
        "amount": {
        "currency": "RUB",
        "value": amount_new
      },
      "paymentMethod" : {
        "type": "TOKEN",
        "paymentToken" : paymenttoken
      },
      "customer": {
            "account": accountid
      }
    }
    response = requests.put(f'https://api.qiwi.com/partner/payin/v1/sites/{siteId}/payments/{paymentid}', json=data,
                            headers=headers).json()
    response = json.dumps(response)
    return response

