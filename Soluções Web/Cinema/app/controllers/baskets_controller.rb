class BasketsController < ApplicationController
  def show
    @basket = Basket.find(params[:id])
    @tickets = Ticket.where(basket: @basket.id)
  end

  def confirm
    if params[:confirmed].nil?
      basket = Basket.find(params[:id])
      basket.destroy

      redirect_to root_path
    elsif customer_signed_in?
      basket = Basket.find(params[:id])
      basket.customer = current_customer
      basket.save

      redirect_to "/baskets/" + basket.id.to_s + "/finished"
    else
      redirect_to new_customer_session_path
    end
  end

  def finished
    @basket = Basket.find(params[:id])
  end
end
