class Admin::TheatersController < Admin::ApplicationController
    def index
        @theaters = Theater.all
    end

    def show
        @theater = Theater.find(params[:id])
    end

    def new
        @theater = Theater.new
    end

    def create
        @theater = Theater.new(theater_params)

        if @theater.save
            redirect_to @theater
        else
            render 'new'
        end
    end

    def edit
        @theater = Theater.find(params[:id])
    end

    def update
        @theater = Theater.find(params[:id])

        if @theater.update(theater_params)
            redirect_to @theater
        else
            render 'edit'
        end
    end

    def destroy
        @theater = Theater.find(params[:id])
        @theater.destroy
        redirect_to theaters_path
    end

    private
    def theater_params
        params.require(:theater).permit(:name, :address, :address_number, :city, :state)
    end
end
