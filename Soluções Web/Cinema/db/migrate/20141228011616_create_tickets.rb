class CreateTickets < ActiveRecord::Migration
  def change
    create_table :tickets do |t|
      t.references :session, index: true
      t.references :basket, index: true
      t.integer :row
      t.integer :seat

      t.timestamps
    end
  end
end
