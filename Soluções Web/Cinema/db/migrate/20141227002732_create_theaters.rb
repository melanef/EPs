class CreateTheaters < ActiveRecord::Migration
  def change
    create_table :theaters do |t|
      t.string :name
      t.string :address
      t.integer :address_number
      t.string :city
      t.string :state

      t.timestamps
    end
  end
end
