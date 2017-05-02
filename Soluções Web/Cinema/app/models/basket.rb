class Basket < ActiveRecord::Base
  belongs_to :customer
  has_many :tickets
end
