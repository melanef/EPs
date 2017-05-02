class Admin::RoomsController < Admin::ApplicationController
    def index
        @theater = Theater.find(params[:theater_id])
        @rooms = @theater.rooms
    end

    def show
        @theater = Theater.find(params[:theater_id])
        @room = Room.find(params[:id])
    end

    def new
        @theater = Theater.find(params[:theater_id])
        @room = Room.new
    end

    def create
        @theater = Theater.find(params[:theater_id])
        @room = @theater.rooms.create(room_params)

        redirect_to admin_theater_path(@theater)
    end

    def edit
        @theater = Theater.find(params[:theater_id])
        @room = Room.find(params[:id])
    end

    def update
        @theater = Theater.find(params[:theater_id])
        @room = Room.find(params[:id])

        if @room.update(room_params)
            redirect_to admin_theater_rooms_path(@theater)
        else
            render 'edit'
        end
    end

    def destroy
        @theater = Theater.find(params[:theater_id])
        @room = Room.find(params[:id])
        @room.destroy
        redirect_to admin_theater_rooms_path(@theater)
    end

    private
    def room_params
        params.require(:room).permit(:name, :rows, :seats_per_row)
    end
end
