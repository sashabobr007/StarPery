import psycopg2
class Database():
    def __init__(self):
        self.con = psycopg2.connect(database='qiwi',
                                    user='alex',
                                    password='1712',
                                    host='127.0.0.1',
                                    port='5432'
                                    )

    def new_user(self, phone):
        cursor = self.con.cursor()
        cursor.execute(f"insert into users (phone) values ('{phone}')")
        self.con.commit()
        self.con.close()

    def update_trans(self, token):
        cursor = self.con.cursor()
        cursor.execute(f"update tranzactions set status = 'payment_succeded' where token = '{token}'")
        self.con.commit()
        self.con.close()

    def new_trans(self, phone, token):
        cursor = self.con.cursor()
        cursor.execute(f"select id from users where phone = '{phone}'")
        id = cursor.fetchall()[0][0]
        cursor.execute(f"insert into usertrans (id_user, token) values ('{id}' ,'{token}')")
        cursor.execute(f"insert into tranzactions (token, status) values ('{token}' ,'token_created')")
        self.con.commit()
        self.con.close()

