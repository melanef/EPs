class CreateRooms < ActiveRecord::Migration
  def change
    create_table :rooms do |t|
      t.string :name
      t.references :theater, index: true
      t.integer :rows
      t.integer :seats_per_row
      t.timestamps
    end
  end
end
