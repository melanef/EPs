class Theater < ActiveRecord::Base
    has_many :rooms
    validates :name , presence: true , length: { minimum: 5 }
    validates :address , presence: true
    validates :address_number , presence: true
    validates :city , presence: true
    validates :state , presence: true
end
