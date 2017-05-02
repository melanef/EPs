class CreateBaskets < ActiveRecord::Migration
  def change
    create_table :baskets do |t|
      t.references :customer, index: true

      t.timestamps
    end
  end
end
