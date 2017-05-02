class SessionsController < ApplicationController
    def choose_seat
        @session = Session.find(params[:id])
        @room = @session.room
        @theater = @room.theater
        @movie = @session.movie
    end

    def buy
        session = Session.find(params[:id])
        seats = params[:ticket_seats]

        selected_seats = []
        seats.keys.each do |seat|
            if params[:ticket_seats][seat] == "1"
                selected_seats << seat.split(",")
            end
        end

        basket = Basket.new
        basket.save

        selected_seats.each do |seat|
            Ticket.create(:session => session, :basket => basket, :row => seat[0], :seat => seat[1] )
        end

        redirect_to basket_path(basket)
    end
end