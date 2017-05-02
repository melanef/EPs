Customer.create!([
  {email: "teste@example.com", encrypted_password: "$2a$10$FIlHiPisytCrETcDWOAhnuPWstJcH/YA4ivT0NNu.2rVdjpSwAXDO", reset_password_token: nil, reset_password_sent_at: nil, remember_created_at: nil, sign_in_count: 1, current_sign_in_at: "2015-01-11 14:37:37", last_sign_in_at: "2015-01-11 14:37:37", current_sign_in_ip: "177.208.17.157", last_sign_in_ip: "177.208.17.157"}
])

User.create!([
  {email: "teste@example.com", encrypted_password: "$2a$10$FIlHiPisytCrETcDWOAhnuPWstJcH/YA4ivT0NNu.2rVdjpSwAXDO", reset_password_token: nil, reset_password_sent_at: nil, remember_created_at: nil, sign_in_count: 6, current_sign_in_at: "2015-01-10 02:08:42", last_sign_in_at: "2015-01-06 00:32:05", current_sign_in_ip: "177.208.17.157", last_sign_in_ip: "177.208.17.157", name: "Teste", surname: "Teste"}
])


cinema1 = Theater.create!({name: "Shopping Eldorado", address: "Avenida Rebouças", address_number: 3005, city: "São Paulo", state: "SP"})
cinema2 = Theater.create!({name: "New York City Center", address: "Avenida das Americas", address_number: 1000, city: "Rio de Janeiro", state: "RJ"})

filme1 = Movie.create!({name: "Indiana Jones e a Bussola de Ouro", debut: "1989-03-28", summary: "Indiana Jones salva o mundo"})
filme2 = Movie.create!({name: "Harry Potter e o Festim dos Corvos", debut: "2015-01-01", summary: "Harry Potter passa a faca em todo o mundo"})

sala1 = Room.create!({name: "Tigre", theater_id: cinema1.id, rows: 50, seats_per_row: 20})
sala2 = Room.create!({name: "Cobra", theater_id: cinema1.id, rows: 75, seats_per_row: 30})
sala3 = Room.create!({name: "1", theater_id: cinema2.id, rows: 20, seats_per_row: 50})
sala4 = Room.create!({name: "2", theater_id: cinema2.id, rows: 10, seats_per_row: 12})
sala5 = Room.create!({name: "3", theater_id: cinema2.id, rows: 6, seats_per_row: 20})

Session.create!([
  {room_id: sala1.id, movie_id: filme1.id, datetime: "2014-12-27 18:00:00"},
  {room_id: sala2.id, movie_id: filme1.id, datetime: "2014-12-27 20:30:00"},
  {room_id: sala3.id, movie_id: filme1.id, datetime: "2015-01-01 20:30:00"},
  {room_id: sala4.id, movie_id: filme1.id, datetime: "2015-01-01 23:00:00"},
  {room_id: sala5.id, movie_id: filme1.id, datetime: "2015-01-01 17:00:00"},
  {room_id: sala1.id, movie_id: filme2.id, datetime: "2014-12-27 20:30:00"},
  {room_id: sala2.id, movie_id: filme2.id, datetime: "2014-12-27 18:00:00"},
  {room_id: sala3.id, movie_id: filme2.id, datetime: "2015-01-01 17:00:00"},
  {room_id: sala4.id, movie_id: filme2.id, datetime: "2015-01-01 20:30:00"},
  {room_id: sala5.id, movie_id: filme2.id, datetime: "2015-01-01 23:00:00"}
])

Basket.create!()
Ticket.create!()
