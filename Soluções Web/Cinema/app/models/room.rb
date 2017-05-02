class Room < ActiveRecord::Base
    belongs_to :theater
    has_many :sessions
    validates :name , presence: true
    validates :rows , presence: true
    validates :seats_per_row , presence: true
end
