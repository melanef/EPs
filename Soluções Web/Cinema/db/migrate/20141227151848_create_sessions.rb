class CreateSessions < ActiveRecord::Migration
  def change
    create_table :sessions do |t|
      t.references :room, index: true
      t.references :movie, index: true
      t.datetime :datetime, index: true

      t.timestamps
    end
  end
end
