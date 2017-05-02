class Movie < ActiveRecord::Base
    has_many :sessions
    validates :name , presence: true
    validates :summary , presence: true
    validates :debut , presence: true
end
