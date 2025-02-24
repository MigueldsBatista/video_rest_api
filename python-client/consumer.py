import base64

class VideoAppClient:

    def __init__(self, client_id, client_secret):
        self.client_id = client_id
        self.client_secret = client_secret

    def get_token(self):
        url="http://localhost:8080/oauth2/token"
        headers = {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic ' + base64.b64encode((self.client_id + ':' + self.client_secret).encode()).decode()
        }

        data = {
            'grant_type': 'client_credentials'
            
        }