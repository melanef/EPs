class Session < ActiveRecord::Base
  belongs_to :room
  belongs_to :movie
  has_many :tickets
  validates :datetime , presence: true
end
