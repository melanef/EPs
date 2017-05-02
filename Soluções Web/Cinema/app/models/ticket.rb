class Ticket < ActiveRecord::Base
  belongs_to :session
  belongs_to :basket
end
