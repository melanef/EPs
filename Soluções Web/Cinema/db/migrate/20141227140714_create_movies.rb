class CreateMovies < ActiveRecord::Migration
  def change
    create_table :movies do |t|
      t.string :name
      t.date :debut
      t.text :summary

      t.timestamps
    end
  end
end
